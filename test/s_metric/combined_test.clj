(ns s-metric.combined-test
  (:require [midje.sweet :refer :all]
            [s-metric.combined :refer :all]))

(facts "combined match"
       (fact "match sum up to final score"
             (match "123" "123") => (+ 7 3)
             (match "ta" "abcdtargetefab") => 2
             (match "ta" "tabcdtargetefab") => (+ 4 2)
             (match "abcd" "ab123bc123cd123") => (+ 6 2)
             (match "abcd" "ab1abc23bc123cd123") => (+ 13 2)
             (match "18BA4471695" "95BA403053") => (+ 9 3)
             (match "1234567890A" "1234567890A") => 286))

(facts "combined percentage"
       (fact "match sum up to final score"
             (match-% "123" "123") => 100.
             (match-% "ta" "tabcdtargetefab") => 0.8823529411764706
             (match-% "abcd" "ab123bc123cd123") => 1.176470588235294
             (match-% "abcd" "ab1abc23bc123cd123") => 1.315789473684211
             (match-% "95BA4471695" "95BA403053") => 16.7420814479638))
