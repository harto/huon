# Huon [![Build Status](https://travis-ci.org/harto/huon.svg?branch=master)](https://travis-ci.org/harto/huon)

A console.log wrapper for ClojureScript.


## Goals

 * Lazy message evaluation
 * Simple API


## Non-goals

 * Highly configurable


## Installation

Add `[org.harto/huon "1.0.0-SNAPSHOT"]` as a dependency in `project.clj` (or similar).


## Basic usage

```cljs
(ns foo.bar
  (:require [huon.log :as log]))

;; optional; defaults to :warn
(log/configure! {:root-level :info})

(log/debug "an invisible message")
(log/info "hello" "world")
```

Output:
```
[foo.bar:8] hello world
```

Available logging macros are `debug`, `info`, `warn` and `error`.


## `time` macro

The `time` macro wraps `console.time` / `console.timeEnd` for basic profiling:
```cljs
(log/time "reticulating splines"
  (reticulate-splines))
```

Output:
```
[foo.bar:27] reticulating splines: 37.192ms
```


## Additional API functions

 - `(configure! opts)` - set logging configuration according to options
 - `(set-root-level! level)` - reset the root logger level (e.g. `(set-root-level! :error)`)
 - `(set-level! logger-name level)` - reset a logger level (e.g. `(set-level! "foo.bar" :warn)`)


## Configuration options

 - `:show-level?` - whether to print the log level alongside each message (default: `false`)
 - `:format` - a function to customize formatting of each message argument (default: `str`)
 - `:root-level` - the global logging threshold (default: `:warn`)
 - `:logger-levels` - a mapping of namespace names to log levels (e.g. `{"foo" :info, "foo.bar" :debug}`)


## Development

The automated test suite is runnable via `bin/run-tests`.


## License

MIT; see [LICENSE](./LICENSE).
