(ns huon.log
  (:require [clojure.string] ; required by macros
            [goog.debug.Console]
            [goog.debug.Logger.Level :as Level]
            [goog.debug.LogManager :as LogManager])
  ;; automatically include in requiring namespaces
  (:require-macros huon.log))

(LogManager/initialize)

(defonce console
  (goog.debug.Console.))

(defn enable!
  "Start capturing console output. Apps that want to display log output should
  call this function, but libraries that depend on Huon for logging should not."
  []
  (.setCapturing console true))

(def ^:private levels
  {:debug Level/FINE
   :info  Level/INFO
   :warn  Level/WARNING
   :error Level/SEVERE})

(defn set-level!
  "Set a per-namespace logging level. This overrides the level defined by
  (set-root-level!)."
  [logger-or-name level]
  {:pre [(contains? levels level)]}
  (let [logger (if (string? logger-or-name)
                 (LogManager/getLogger logger-or-name)
                 logger-or-name)]
    (.setLevel logger (levels level))))

(defn set-root-level!
  "Set the global logging threshold. This can be overridden on a per-namespace
  basis with (set-level!)."
  [level]
  (set-level! (LogManager/getRoot) level))

(defn log* [logger-name level msg-fn]
  {:pre [(contains? levels level)]}
  (let [logger (get-logger logger-name)
        glevel (levels level)]
    (if (.isLoggable logger glevel)
      (.log logger glevel msg-fn))))
