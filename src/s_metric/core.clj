(ns ^:skip-aot s-metric.core
  (:require [clojure.tools.cli :refer [cli]]
            [s-metric.protocols :as p]
            [s-metric.padded-hamming]
            [s-metric.naive-match]
            [s-metric.levenshtein]
            [s-metric.combined])
  (:import [s_metric.padded_hamming HammingDistance]
           [s_metric.naive_match NaiveDistance]
           [s_metric.levenshtein LevenshteinDistance]
           [s_metric.combined Combined])
  (:gen-class))

(def combined (Combined. [(HammingDistance.) (NaiveDistance.) (LevenshteinDistance.)]))

(def ^:dynamic *debug* false)

(def options [
              ["-l" "--levenshtein" "use http://en.wikipedia.org/wiki/Levenshtein_distance" :flag true :default true]
              ["-h" "--hamming" "use http://en.wikipedia.org/wiki/Hamming_distance with padding converted to a score over the max total" :flag true :default false]
              ["-n" "--naive" "Use naive matching, comparing all combinations of contigous substrings" :flag true :default false]
              ["-a" "--all" "Combine all distance metrics together and output the total match score" :flag true :default false]
              ["-v" "--verbose" "Add detailed output" :flag true :default true]
              ["-t" "--test" "just echoes the argument list and exits" :flag true]])

(defn mandatory-args? [args]
  (< (count (remove #(= "" %) args)) 2))

(defn print-msg-and-exit [msg]
  (do 
    (println msg)
    (System/exit 1)))

(defn as-score [score]
  "Convert a distance to a printable score"
  (format "Score: %s%%" score))

(defn -main [& args]
  (let [[opts args banner] (apply cli args options)]
    (binding [*debug* (:verbose opts)]
      (cond 
        (:test opts) (print-msg-and-exit (str " Args echo: " (seq args) "\n(To mesure JVM startup time just prepend 'time' to this command.)"))
        (mandatory-args? args) (print-msg-and-exit (str "You need at list two strings to output their score. Usage: s-string [-ht] <first-string> <second-string>\n" banner))
        (:levenshtein opts) (println "Levenshtein" (as-score (p/match-% (LevenshteinDistance.) (first args) (last args))))
        (:hamming opts) (println "Hamming" (as-score (p/match-% (HammingDistance.) (first args) (last args))))
        (:naive opts) (println "Naive" (as-score (p/match-% (NaiveDistance.) (first args) (last args))))
        (:all opts) (println "Combined" (as-score (p/match-% combined (first args) (last args))))))))
