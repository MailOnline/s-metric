(ns s-metric.bulk
  (:require [s-metric.protocols :as p]
            [clojure.set :as s]
            [s-metric.levenshtein]
            [clojure.core.reducers :as r])
  (:import [s_metric.levenshtein LevenshteinDistance]))

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
   (top-scores s xs n (LevenshteinDistance.)))
  ([s xs n metrics]
   (let [xs (->> xs
                 (into [])
                 (r/map #(-> [(p/match-% metrics s %) %])))]
     (r/fold (r/monoid (combinef n) sorted-set) (reducef n) xs))))

(defn mispelled [s xs]
  "Just return the highest of the top-scores"
  (last (top-scores s xs)))
