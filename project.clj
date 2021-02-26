(defproject affinity "0.0.1"
  :description "Sentiment Analysis for Clojure using AFINN-165"
  :url "https://github.com/pyxylyze/affinity"
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :repl-options {:init-ns affinity.core}
  :profiles {:dev {:dependencies [[midje "1.9.6"]]}})
