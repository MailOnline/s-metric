(defproject s-metric "0.0.3"
  :description "collection of metrics to measure string distances"
  :url "https://github.com/MailOnline/s-metric"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.cli "0.3.1"]
                 [boost "0.1.2"]]
  :main s-metric.core
  :uberjar-name "s-metric.jar"
  :repl-options {:init 
                 (do (require 'midje.repl) (midje.repl/autotest)) :init-ns user}
  :documentation {:files {
                          "docs/combined"
                          {:input "test/s_metric/combined_test.clj"
                           :title "Combined Metrics"
                           :sub-title "Put all metrics together in a single call"
                           :author "reborg"
                           :email  "reborg@reborg.net"}
                          "docs/naive_match"
                          {:input "test/s_metric/naive_match_test.clj"
                           :title "Naive Comparison"
                           :sub-title "simpler way of comparing two strings"
                           :author "reborg"
                           :email  "reborg@reborg.net"}
                          "docs/hamming"
                          {:input "test/s_metric/hamming_test.clj"
                           :title "Hamming with padding"
                           :author "reborg"
                           :email  "reborg@reborg.net"}}}
  :profiles {:uberjar 
             {:main s-metric.core, :aot :all}
             :dev 
             {:dependencies [[midje "1.6.3"]
                             [criterium "0.4.3"]
                             [xrepl "0.1.2"]]
              :plugins [[lein-midje "3.1.3"]]}})
