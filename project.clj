(defproject s-metric "0.0.4"
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
                          "docs/core"
                          {:input "test/s_metric/core_test.clj"
                           :title "General examples"
                           :sub-title ""
                           :author "reborg"
                           :email  "reborg@reborg.net"}
                          "docs/bulk"
                          {:input "test/s_metric/bulk_test.clj"
                           :title "Batch scoring"
                           :sub-title "Scores can be calculated on a list of target strings."
                           :author "reborg"
                           :email  "reborg@reborg.net"}
                          "docs/levenshtein"
                          {:input "test/s_metric/levenshtein_test.clj"
                           :title "Levenshtein Distance"
                           :sub-title "Find the minimum distance between two strings."
                           :author "reborg"
                           :email  "reborg@reborg.net"}
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
                          {:input "test/s_metric/padded_hamming_test.clj"
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
