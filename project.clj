(defproject s-metric "0.0.2"
  :description "Library to score string distances based on a collection of distance metrics."
  :url "https://github.com/MailOnline/s-metric"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.cli "0.3.1"]
                 [boost "0.1.2"]]
  :main s-metric.core
  :uberjar-name "s-metric.jar"
  :repl-options {:init (do (require 'midje.repl) (midje.repl/autotest))}
  :generators [[lein-gen/generators "0.1.0"]]
  :profiles {:uberjar 
             {:main s-metric.core, :aot :all}
             :dev 
             {:dependencies [[midje "1.6.3"]
                             [criterium "0.4.3"]
                             [xrepl "0.1.2"]]
              :plugins [[lein-midje "3.1.1"]
                        [lein-gen "0.1.0"]]}})
