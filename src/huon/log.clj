(ns huon.log)

(defmacro ^:private log [level args]
  `(log* ~(str *ns*)
         ~level
         #(clojure.string/join " " ~(mapv (fn [x] `(str ~x)) args))))

(defmacro debug
  "Evaluate and log args if level >= :debug"
  [& args]
  `(log :debug ~args))

(defmacro info
  "Evaluate and log args if level >= :info"
  [& args]
  `(log :info ~args))

(defmacro warn
  "Evaluate and log args if level >= :warn"
  [& args]
  `(log :warn ~args))

(defmacro error
  "Evaluate and log args if level >= :error"
  [& args]
  `(log :error ~args))
