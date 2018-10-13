(ns huon.tests
  (:require [huon.log :as log]
            [huon.tests-2]))

(enable-console-print!)
(log/enable!)

(defn ^:export main []
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
  (log/debug #js {:foo 42})

  ;; check level override
  (log/set-level! "huon.tests-2" :warn)
  (log/info "should see this")
  (huon.tests-2/log-info "shouldn't see this")
  )

(set! *main-cli-fn* main)
