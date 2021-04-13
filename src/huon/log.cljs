(ns huon.log
  (:require [clojure.string :as str])  ; N.B.: also required by macros
  (:require-macros huon.log))          ; auto-include in requiring namespaces

(defonce ^:private levels
  {:debug 1
   :info 2
   :warn 3
   :error 4})

(defonce ^:private config
  (atom {:root-level :warn
         :logger-levels {}
         ;; Don't print name of log level by default, because messages are often
         ;; colour-coded to indicate level in browser consoles.
         :show-level? false
         ;; Stringify message arguments by default. This is a reasonable setting
         ;; for consoles that don't know how to format ClojureScript objects in
         ;; a readable way. Developers using extensions like cljs-devtools may
         ;; want to set this to `nil`, so that arguments are passed through
         ;; untouched and formatted by that extension.
         :format str}))

(defonce ^:private logger-levels-cache
  (atom {}))

(defn configure!
  "Configures logging for your application.

  Available options are:
    :root-level - the default logging threshold
    :logger-levels - a mapping of namespace names to log levels
    :show-level? - whether to print the log level alongside individual messages
    :format - a function to optionally format each message component

  See `config` for defaults."
  [opts]
  (swap! config merge opts)
  (reset! logger-levels-cache {}))

(defn set-root-level!
  "Sets the global logging threshold. This can also be configured with the
  :root-level initialization option."
  [level]
  {:pre [(contains? levels level)]}
  (swap! config assoc :root-level level)
  (reset! logger-levels-cache {}))

(defn set-level!
  "Sets a per-namespace logging level. This can also be configured with the
  :logger-levels initialization option."
  ([level]
   (set-level! (str *ns*) level))
  ([logger-name level]
   {:pre [(string? logger-name)
          (contains? levels level)]}
   (swap! config assoc-in [:logger-levels logger-name] level)
   (reset! logger-levels-cache {})))

(defn- parent [logger]
  (when-let [last-segment-pos (str/last-index-of logger #"\.")]
    (subs logger 0 last-segment-pos)))

(defn- configured-level [logger]
  (let [configured-levels (:logger-levels @config)]
    (loop [logger logger]
      (or (configured-levels logger)
          (if-let [p (parent logger)]
            (recur p)
            (:root-level @config))))))

(defn- log?
  "Test if logger should log a message at the given level."
  [logger msg-level]
  (let [msg-threshold (levels msg-level)]
    (if-let [cached-level (@logger-levels-cache logger)]
      (>= msg-threshold (levels cached-level))
      (let [level (configured-level logger)]
        (swap! logger-levels-cache assoc logger level)
        (>= msg-threshold (levels level))))))

;; Functions referenced by macros

(defn log* [logger msg-level formatted-src formatted-level eval-args]
  {:pre [(contains? levels msg-level)]}
  (when (log? logger msg-level)
    (let [args (eval-args)
          format-arg (:format @config)
          formatted-args (if format-arg (map format-arg args) args)
          msg (if (:show-level? @config)
                (conj formatted-args formatted-level formatted-src)
                (conj formatted-args formatted-src))]
      (apply (aget js/console (name msg-level)) msg))))

(defn time* [label eval-body]
  (.time js/console label)
  (let [res (eval-body)]
    (.timeEnd js/console label)
    res))
