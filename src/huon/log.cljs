(ns huon.log
  (:require [clojure.string] ; required by macros
            [goog.debug.Console]
            [goog.debug.Logger.Level :as Level]
            [goog.debug.LogManager :as LogManager])
  ;; automatically include in requiring namespaces
  (:require-macros huon.log))

(def ^:private levels
  {:debug Level/FINE
   :info  Level/INFO
   :warn  Level/WARNING
   :error Level/SEVERE})

(defonce ^:private init
  (do
    (LogManager/initialize)
    (.setCapturing (goog.debug.Console.) true)
    nil))

(defn set-root-level! [level]
  {:pre [(contains? levels level)]}
  (.setLevel (LogManager/getRoot) (levels level)))

(defn log* [logger-name level msg-fn]
  {:pre [(contains? levels level)]}
  (let [logger (LogManager/getLogger logger-name)
        glevel (levels level)]
    (if (.isLoggable logger glevel)
      (.log logger glevel msg-fn))))
