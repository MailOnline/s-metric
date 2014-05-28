(ns s-metric.combined
  (:require [s-metric.hamming :as hamming]
            [s-metric.naive-match :as naive]))

(defn match [s target]
  "Combined match score for s into target using all available metrics"
  (let [naive (naive/match s target)
        hamming (hamming/match s target)]
    (+ naive hamming)))

(defn best-score [s target]
  "Calculate the best score for all metrics combined"
  (let [hscore (hamming/best-score s target) 
        nscore (naive/best-score target)]
    (+ hscore nscore)))

(defn match-% [s target]
  "Return a match score as percentage for all the distance metrics combined"
  (let [match (match s target)
        best (best-score s target)]
    (.doubleValue (with-precision 3 (* (/ match best) 100M)))))
