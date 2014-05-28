(ns s-metric.hamming)

(defn pad [s size]
  "Fill s with filler up to size."
  (let [padded (str "%-" size "s")]
    (format padded s)))

(defn make-same-size [x y]
  (cond
    (= (count x) (count y)) [x y]
    (> (count x) (count y)) [x (pad y (count x))]
    (< (count x) (count y)) [(pad x (count y)) y]))

(defn distance [s target]
  "Calculate the hamming distance between s and target, right padding
  the shortest to the left if necessary."
  (let [[x y] (make-same-size s target)]
    (count (filter true? (map (partial reduce not=) (map vector x y))))))

(defn match [s target]
  "Return a matching score as the inverse of the hamming distance."
  (let [mmax (max (count s) (count target))]
    (- mmax (if (< (count s) (count target)) 
              (distance s target)
              (distance target s)))))

(defn best-score [s target]
  "Calculate the miminum hamming distance for a n-length string s"
  (max (count s) (count target)))

(defn match-% [s target]
  "Return a match score as percentage as the ratio between the score
  and the maximum score achievable."
  (let [match (match s target)
        best (best-score s target)]
    (* (/ match best) 100.)))
