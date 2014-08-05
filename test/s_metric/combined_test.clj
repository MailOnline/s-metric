(ns s-metric.combined-test
  (:require [midje.sweet :refer :all]
            [s-metric.protocols :as p]
            [s-metric.combined :as c])
  (:import [s_metric.combined Combined]))

(facts "combined match"
       (fact "match sum up to final score"
             (p/match (Combined.) "123" "123") => (+ 7 3)
             (p/match (Combined.) "ta" "abcdtargetefab") => 2
             (p/match (Combined.) "ta" "tabcdtargetefab") => (+ 4 2)
             (p/match (Combined.) "abcd" "ab123bc123cd123") => (+ 6 2)
             (p/match (Combined.) "abcd" "ab1abc23bc123cd123") => (+ 13 2)
             (p/match (Combined.) "18BA4471695" "95BA403053") => (+ 9 3)
             (p/match (Combined.) "1234567890A" "1234567890A") => 286))

(facts "combined percentage"
       (fact "match sum up to final score"
             (p/match-% (Combined.) "123" "123") => 100.
             (p/match-% (Combined.) "ta" "tabcdtargetefab") => 0.8823529411764706
             (p/match-% (Combined.) "abcd" "ab123bc123cd123") => 1.176470588235294
             (p/match-% (Combined.) "abcd" "ab1abc23bc123cd123") => 1.315789473684211
             (p/match-% (Combined.) "95BA4471695" "z5BA4471695") => 77.62237762237763))

(defn rand-str [n]
  (.substring (clojure.string/replace (java.util.UUID/randomUUID) #"-" "") 0 n))

(facts "bulk matching"
       (let [target (into [] (map (fn [n] (rand-str 10)) (range 10000)))]
         (fact "it only returns the top n"
               (time (count (c/top-scores "f11k45k" target 20))) => 20)))
