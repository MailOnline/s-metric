(ns s-metric.levenshtein
  (:require [s-metric.protocols :as p]))

(set! *warn-on-reflection* true)

(defn nextelt
  "Given two characters, the previous row, and a row we are
  building, determine out the next element for this row."
  [char1 char2 prevrow thisrow position]
  (if (= char1 char2)
    (prevrow (- position 1))
    (+ 1 (min
           (prevrow (- position 1))
           (prevrow position)
           (last thisrow)))))

(defn nextrow
  "Based on the next character from string1 and the whole of string2
  calculate the next row. Initially thisrow contains one number."
  [char1 str2 prevrow thisrow]
  (let [char2 (first str2)
        position (count thisrow)]
    (if (= (count thisrow) (count prevrow))
      thisrow
      (recur
        char1
        (rest str2)
        prevrow
        (conj thisrow (nextelt char1 char2 prevrow thisrow position))))))

(defn distance
  ([str1 str2]
   (let [row0 (vec (map first (map vector (iterate inc 1) str2)))]
     (distance 1 (vec (cons 0 row0)) str1 str2)))
  ([row-nr prevrow ^String str1 ^String str2]
   (let [next-row (nextrow (first str1) str2 prevrow (vector row-nr))
         str1-remainder (.substring str1 1)]
     (if (= "" str1-remainder)
       (last next-row)
       (recur (inc row-nr) next-row str1-remainder str2)))))

(defn best-score [_ s target]
  (max (count s) (count target)))

(defn match [_ s target]
  (- (best-score _ s target) (distance s target)))

(deftype LevenshteinDistance [])

(extend LevenshteinDistance
  p/Score
  (assoc p/ScoreImpl
         :match #'match
         :best-score #'best-score))
