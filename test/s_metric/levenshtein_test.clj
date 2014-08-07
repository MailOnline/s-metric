(ns s-metric.levenshtein-test
  (:require [midje.sweet :refer :all]
            [s-metric.levenshtein :as l]
            [s-metric.protocols :as p])
  (:import [s_metric.levenshtein LevenshteinDistance]))

(facts "percentage match"
       (fact "one off"
             (p/match-% (LevenshteinDistance.) "A" "AB") => 50.0
             (p/match-% (LevenshteinDistance.) "ABC" "DABC") => 75.0
             (p/match-% (LevenshteinDistance.) "12345" "01234") => 60.0
             (p/match-% (LevenshteinDistance.) "124QA075228" "124QA05228") => 90.91))
