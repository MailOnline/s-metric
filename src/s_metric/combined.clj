(ns s-metric.combined
  (:require [s-metric.hamming :as hamming]
            [s-metric.naive-match :as naive]
            [s-metric.protocols :as p]
            [clojure.set :as s]
            [clojure.core.reducers :as r]))

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

(defn reducef [n]
  (fn [topn score]
    (let [top (conj topn score)
          lowest (first top)]
      (if (> (count top) n)
        (s/difference top #{lowest})
        top))))

(defn combinef [n]
  (fn [top1 top2]
    (let [top (s/union top1 top2)
          lowest (take n top)]
      (s/difference top (into (sorted-set) lowest)))))

(defn top-scores
  "Applies match-% to a collection of targets returning the top <= n highest scores as
  [score xs] pairs in an ordered set."
  ([s xs]
   (top-scores s xs 20))
  ([s xs n]
   (let [combined (Combined.)
         xs (->> xs
                 (into [])
                 (r/map #(-> [(match-% combined s %) %])))]
     (r/fold (r/monoid (combinef n) sorted-set) (reducef n) xs))))
