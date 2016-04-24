(ns huon.tests
  (:require [cljs.test :refer-macros [run-tests]]
            [cljs.nodejs]
            [huon.log :as log]))

(enable-console-print!)

(defn main []
  ;; check messages not logged below threshold
  (doseq [[i level] (map-indexed vector [:debug :info :warn :error])]
    (log/set-root-level! level)
    (log/debug "debug" i)
    (log/info "info" i)
    (log/warn "warn" i)
    (log/error "error" i))

  ;; check messages not evaluated below threshold
  (log/set-root-level! :error)
  (log/info (throw (js/Error. "shouldn't get here")))

  ;; check formatting of various objects
  (log/set-root-level! :debug)
  (log/debug {:foo "bar" :baz 42})
  (log/debug '(a b c d foo yadda-yadda))
  (log/debug #js {:foo 42}))

(set! *main-cli-fn* main)
