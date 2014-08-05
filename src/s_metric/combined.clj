(ns s-metric.combined
  (:require [s-metric.hamming :as hamming]
            [s-metric.naive-match :as naive]
            [s-metric.protocols :as p]))

(defn match [_ s target]
  "Combined match score for s into target using all available metrics"
  (let [naive (naive/match _ s target)
        hamming (hamming/match _ s target)]
    (+ naive hamming)))

(defn best-score [_ s target]
  "Calculate the best score for all metrics combined"
  (let [hscore (hamming/best-score _ s target)
        nscore (naive/best-score _ "" target)]
    (+ hscore nscore)))

(defn match-% [_ s target]
  "Return a match score as percentage for all the distance metrics combined"
  (let [match (match _ s target)
        best (best-score _ s target)]
    (* (/ match best) 100.)))

(deftype Combined [])

(extend Combined
  p/Score
  {:match #'match
   :best-score #'best-score
   :match-% #'match-%})
