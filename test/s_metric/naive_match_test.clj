(ns s-metric.naive-match-test
  (:require [midje.sweet :refer :all]
            [criterium.core :as b]
            [clojure.string :as s]
            [s-metric.naive-match :refer :all]))

(facts "scoring"
       (fact "accumulates points for each match based on string lenght"
             (score "3w" ["12" "3w" "er"]) => 2
             (score "3w" ["12" "3w" "3w"]) => 4
             (score "aaa" ["aaa" "bbb" "aa" "aaa"]) => 6)
       (fact "accumulation of multiple scores over the same options"
             (score-all ["aa" "bb" "cc"] ["12" "aa" "aa" "bb" "12"]) => 6))

(facts "splitting into subcollections"
       (fact "all contigous pairs"
             (contigous 2 ["a" "s" "d" "f"]) => [["a" "s"] ["s" "d"] ["d" "f"]])
       (fact "all contigous triplets"
             (contigous 3 ["a" "s" "d" "f"]) => [["a" "s""d"] ["s" "d" "f"]])
       (fact "all contigous qudruplets and more"
             (contigous 4 ["a" "s" "d" "f"]) => [["a" "s" "d" "f"]]
             (contigous 4 ["a" "s" "d" "f" "g"]) => [["a" "s" "d" "f"] ["s" "d" "f" "g"]]))

(facts "splitting to strings"
       (fact "all contigous pairs"
             (contigous-str 2 "as") => ["as"]
             (contigous-str 2 "asdf") => ["as" "sd" "df"]))

(facts "easy check on string pairs"
       (fact "no matches"
             (matching ["ab" "bc" "cd"] "123456678891234") => [])
       (fact "matches"
             (matching ["ab" "bc" "cd"] "12345ab38438476") => ["ab"]
             (matching ["ab" "bc" "cd"] "12345ab38bc8476") => ["ab" "bc"]))

(facts "scoring on strings"
       (fact "one occurrence of a 5 letter string"
             (match "ta" "abcdtargetefab") => 2
             (match "ta" "tabcdtargetefab") => 4
             (match "abcd" "ab123bc123cd123") => 6
             (match "abcd" "ab1abc23bc123cd123") => 13
             (match "18BA4471695" "95BA403053") => 9
             (match "1234567890A" "1234564890A") => 66 ;; exponential drop
             (match "1234567890A" "1234567890A") => 275))

(facts "maximum"
       (fact "max for 11 chars string"
             (best-score "186XG471615") => 275))

(facts "percentages"
       (fact "score as percentage"
             (match-% "123" "123") => 100.
             (match-% "567" "123") => 0.
             (match-% "ababababab" "abab") => 425. ;bogus result, should instead throw error?
             (match-% "18BA4471695" "95BA403053") => 4.285714285714286
             (match-% "95BA447169A" "95BA4471695") => 77.0909090909091))
