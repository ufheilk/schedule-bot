(ns asgnx.core
  (:require [clojure.string :as string]
            [clojure.core.async :as async :refer [go chan <! >!]]
            [asgnx.kvstore :as kvstore
             :refer [put! get! list! remove!]]))

(def help-message
  "There are three different commands you can enter:
1) assist: display this help message
2) <dining location>: get the wait time for this dining location
3) <dining location> <time>: report a wait time for a dining location")

(def dining-locations
  ["pub" "rand" "ebi" "commons" "grins" "kissam"])

;; This is a helper function that you might want to use to implement
;; `cmd` and `args`.
(defn words [msg]
  (if msg
      (string/split msg #" ")
      []))

;; Asgn 1.
;;
;; @Todo: Fill in this function to return the first word in a text
;; message.
;;
;; Example: (cmd "foo bar") => "foo"
;;
;; See the cmd-test in test/asgnx/core_test.clj for the
;; complete specification.
;;
(defn cmd [msg]
  (first (words msg)))

;; Asgn 1.
;;
;; @Todo: Fill in this function to return the list of words following
;; the command in a text message.
;;
;; Example: (args "foo bar baz") => ("bar" "baz")
;;
;; See the args-test in test/asgnx/core_test.clj for the
;; complete specification.
;;
(defn args [msg]
  (rest (words msg)))

;; Asgn 1.
;;
;; @Todo: Fill in this function to return a map with keys for the
;; :cmd and :args parsed from the msg.
;;
;; Example:
;;
;; (parsed-msg "foo bar baz") => {:cmd "foo" :args ["bar" "baz"]}
;;
;; See the parsed-msg-test in test/asgnx/core_test.clj for the
;; complete specification.
;;
(defn parsed-msg [msg]
  {:cmd (clojure.string/lower-case(cmd msg))
   :args (args msg)})

;; Asgn 2.
;;
;; @Todo: Create a function called action-insert that takes
;; a list of keys in a `ks` parameter, a value to bind to that
;; key path to in a `v` parameter, and returns a map with
;; the key :ks bound to the `ks` parameter value and the key :v
;; vound to the `v` parameter value.)
;; The map should also have the key :action bound to the value
;; :assoc-in.
;;
(defn action-insert [ks v]
  {:action :assoc-in
   :ks ks
   :v v})

;; Don't edit!
(defn stateless [f]
  (fn [_ & args]
    [[] (apply f args)]))

;; get the time of the dining location
(defn times-query [state-mgr pmsg]
  (let [location (:cmd pmsg)]
    (get! state-mgr [:location location])))

;; determines if item is contained in vec
(defn in-vec [vec item]
  (some #(= item %) vec))

;; is this pmsg a request to get the time of a location?
(defn is-time-request? [{:keys [cmd args]}]
  (and (in-vec dining-locations cmd) (empty? args)))

;; modfied read-state from asgn4
(defn read-state [state-mgr pmsg]
  (go
   (if (is-time-request? pmsg)
     (<! (times-query state-mgr pmsg))
     {})))

;; response to user who sends unrecognized command
(def default-func
  (stateless (fn [& args] "Unknown command. Respond 'assist' for assistance")))

;; display help message
(def help-func
  (stateless (fn [& args] help-message)))

;; process the time returned by the bucket
(defn proc-time [time]
    (if time [[] time] [[] "There are currently no times available :^\\"]))

;; send wait time to user
(defn report-time [times {:keys [cmd args user-id]}]
  [[(action-insert [:location cmd] (first args))] "Report sent"])

;; returns function to handle state and user ID to find response
(defn create-handler [pmsg]
  (let [cmd (:cmd pmsg)
        args (:args pmsg)
        help (= cmd "assist")]
    (if help help-func
      (if (some #(= cmd %) dining-locations)
        ;; the user is trying to get or update wait times
        (if (empty? args)
          ;; user hasn't added a time -> trying to get a time
          proc-time
          report-time)
        default-func))))

;; add new user to bucket
(defn register-new [user]
  (action-insert [:user user] "registered"))

;; message all new users will see
(def intro-msg
  "I have detected that you have never used this service before. Welcome to SCHEDULE-BOT. There are three commands:
1) assist: display help messages
2) <dining-location>: get wait time for the locations
3) <dining-location> <time>: report a time for a dining locations
If you are reporting a time, be sure that you are accurate as possible and reporting the ~time spent in line at a particular location~
Supported locations are: rand, pub, ebi, commons, kissam, grins
And now, here is the server response to your request:
")

;; get all of the users
(defn get-users [state-mgr user]
  (go
    (<! (get! state-mgr [:user user]))))

;; see if user is new
(defn new-user-check [as o state user-id]
    (if (= state "registered")
      [as o]
      [(conj as (register-new user-id)) (str intro-msg "\n" o)]))


;; Don't edit!
(defn output [o]
  (second o))


;; Don't edit!
(defn actions [o]
  (first o))


;; Don't edit!
(defn invoke [{:keys [effect-handlers] :as system} e]
  (go
    (println "    Invoke:" e)
    (if-let [action (get effect-handlers (:action e))]
      (do
        (println "    Invoking:" action "with" e)
        (<! (action system e))))))


;; Don't edit!
(defn process-actions [system actions]
  (go
    (println "  Processing actions:" actions)
    (let [results (atom [])]
      (doseq [action actions]
        (let [result (<! (invoke system action))]
          (swap! results conj result)))
      @results)))


;; Don't edit!
(defn handle-message
  "
    This function orchestrates the processing of incoming messages
    and glues all of the pieces of the processing pipeline together.

    The basic flow to handle a message is as follows:

    2. Parse the message
    3. Load any saved state that is going to be needed to process
       the message (e.g., querying the list of experts, etc.)
    4. Find the function that can handle the message
    5. Call the handler function with the state from #3 and
       the message
    6. Run the different actions that the handler returned...these actions
       will be bound to different implementations depending on the environemnt
       (e.g., in test, the actions aren't going to send real text messages)
    7. Return the string response to the message

  "
  [{:keys [state-mgr] :as system} src msg]
  (go
    (println "=========================================")
    (println "  Processing:\"" msg "\" from" src)
    (let [
          pmsg   (assoc (parsed-msg msg) :user-id src)
          _      (println "  Parsed msg:" pmsg)
          state  (<! (read-state state-mgr pmsg))
          _      (println "  Read state:" state)
          hdlr   (create-handler pmsg)
          _      (println "  Hdlr:" hdlr)
          [as o] (hdlr state pmsg)
          _      (println "  Hdlr result:" [as o])
          user-state (<! (get-users state-mgr src))
          _ (println " User state:" user-state)
          [as o] (new-user-check as o user-state src)
          arslt  (<! (process-actions system as))
          _      (println "  Action results:" arslt)]
      (println "=========================================")
      o)))
