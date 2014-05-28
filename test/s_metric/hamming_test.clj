(ns s-metric.hamming-test
  (:require [midje.sweet :refer :all]
            [s-metric.hamming :refer :all]))

(facts "zero distance"
       (fact "basic facts"
             (distance "hello" "hello") => 0)
       (fact "numbers are treated the same"
             (distance "1234" "1234") => 0)
       (fact "special chars"
             (distance "12 34" "12 34") => 0
             (distance "12 ?4" "12 ?4") => 0
             (distance "_12 34" "_12 34") => 0))

(facts "non zero distances"
       (fact "one element differs"
             (distance "asdf" "asdd") => 1
             (distance "asdf" "asdz") => 1
             (distance "asd!" "asdz") => 1)
       (fact "different lenght"
             (distance "12345" "012345") => 6
             (distance "123456" "12345") => 1
             (distance "123abc" "123") => 3
             (distance "qwer" "abc134qwer") => 10
             (distance "abc134qwer" "qwer") => 10
             (distance "qwer" "qwerabc134") => 6))

(facts "match score"
       (fact "calculating a score as the inverse of the distance"
             (match "hello" "hello") => 5
             (match "12345" "012345") => 0
             (match "123456" "12345") => 5
             (match "123abc" "123") => 3
             (match "qwer" "abc134qwer") => 0))

(facts "match percentage"
       (fact "percentage from the local maximum"
             (match-% "hello" "hello") => 100.
             (match-% "12345" "012345") => 0.
             (match-% "123456" "12345") => 83.3
             (match-% "12345" "123456") => 83.3
             (match-% "123abc" "123") => 50.
             (match-% "qwer" "abc134qwer") => 0.))

