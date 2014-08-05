(ns s-metric.combined
  (:require [s-metric.protocols :as p]))

(defn match [_ s target]
  "Combined match score for s into target using all available metrics"
  (reduce + 0 (map #(p/match % s target) (.metrics _))))

(defn best-score [_ s target]
  "Calculate the best score for all metrics combined"
  (reduce + 0 (map #(p/best-score % s target) (.metrics _))))

(defn match-% [_ s target]
  "Return a match score as percentage for all the distance metrics combined"
  (let [match (match _ s target)
        best (best-score _ s target)]
    (* (/ match best) 100.)))

(deftype Combined [metrics])

(extend Combined
  p/Score
  {:match #'match
   :best-score #'best-score
   :match-% #'match-%})
