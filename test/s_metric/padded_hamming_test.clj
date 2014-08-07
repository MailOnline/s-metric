(ns s-metric.padded-hamming-test
  (:require [midje.sweet :refer :all]
            [s-metric.padded-hamming :as h]
            [s-metric.protocols :as p])
  (:import [s_metric.padded_hamming HammingDistance]))

(facts "zero distance"
       (fact "basic facts"
             (h/distance "hello" "hello") => 0)
       (fact "numbers are treated the same"
             (h/distance "1234" "1234") => 0)
       (fact "special chars"
             (h/distance "12 34" "12 34") => 0
             (h/distance "12 ?4" "12 ?4") => 0
             (h/distance "_12 34" "_12 34") => 0))

(facts "non zero distances"
       (fact "one element differs"
             (h/distance "asdf" "asdd") => 1
             (h/distance "asdf" "asdz") => 1
             (h/distance "asd!" "asdz") => 1)
       (fact "different lenght"
             (h/distance "12345" "012345") => 6
             (h/distance "123456" "12345") => 1
             (h/distance "123abc" "123") => 3
             (h/distance "qwer" "abc134qwer") => 10
             (h/distance "abc134qwer" "qwer") => 10
             (h/distance "qwer" "qwerabc134") => 6))

(facts "match score"
       (fact "calculating a score as the inverse of the distance"
             (p/match (HammingDistance.) "hello" "hello") => 5
             (p/match (HammingDistance.) "12345" "012345") => 0
             (p/match (HammingDistance.) "123456" "12345") => 5
             (p/match (HammingDistance.) "123abc" "123") => 3
             (p/match (HammingDistance.) "qwer" "abc134qwer") => 0))

(facts "match percentage"
       (fact "percentage from the local maximum"
             (let [hd (HammingDistance.)]
               (p/match-% (HammingDistance.) "hello" "hello") => 100.
               (p/match-% (HammingDistance.) "12345" "012345") => 0.
               (p/match-% (HammingDistance.) "123456" "12345") => 83.33333333333333
               (p/match-% (HammingDistance.) "12345" "123456") => 83.33333333333333
               (p/match-% (HammingDistance.) "123abc" "123") => 50.
               (p/match-% (HammingDistance.) "qwer" "abc134qwer") => 0.)))
