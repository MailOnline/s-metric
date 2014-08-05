(ns s-metric.combined-test
  (:require [midje.sweet :refer :all]
            [s-metric.combined]
            [s-metric.naive-match]
            [s-metric.hamming]
            [s-metric.protocols :as p])
  (:import [s_metric.hamming HammingDistance]
           [s_metric.naive_match NaiveDistance]
           [s_metric.combined Combined]))

(def metrics (Combined. [(HammingDistance.) (NaiveDistance.)]))

(facts "combined match"
       (fact "match sum up to final score"
             (p/match metrics "123" "123") => (+ 7 3)
             (p/match metrics "ta" "abcdtargetefab") => 2
             (p/match metrics "ta" "tabcdtargetefab") => (+ 4 2)
             (p/match metrics "abcd" "ab123bc123cd123") => (+ 6 2)
             (p/match metrics "abcd" "ab1abc23bc123cd123") => (+ 13 2)
             (p/match metrics "18BA4471695" "95BA403053") => (+ 9 3)
             (p/match metrics "1234567890A" "1234567890A") => 286))

(facts "combined percentage"
       (fact "match sum up to final score"
             (p/match-% metrics "123" "123") => 100.
             (p/match-% metrics "ta" "tabcdtargetefab") => 0.8823529411764706
             (p/match-% metrics "abcd" "ab123bc123cd123") => 1.176470588235294
             (p/match-% metrics "abcd" "ab1abc23bc123cd123") => 1.315789473684211
             (p/match-% metrics "95BA4471695" "z5BA4471695") => 77.62237762237763))
