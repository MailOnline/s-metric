(ns s-metric.naive-match
  "Naive match takes the brute force approach of searching all the substrings of size 2 and up
  for the given target string. The score is accumulated based on the length of the matching substring.")

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

(defmacro ? [val]
  `(let [x# ~val]
     (prn '~val '~'is x#)
     x#))

(defn score2 [^String ss ^String s]
"Attempt at using indexOf for speed, no real improvements, unused."
  (let [ss-size (count ss)]
    (loop [last-index 0 counter 0]
      (if (= -1 last-index)
        (* (dec counter) ss-size)
        (recur (.indexOf s ss (+ ss-size last-index)) (inc counter))))))

(defn score [target options]
  "Accumulates a score based on how many times target appears in options, times the lenght
  of target. For example: (score \"aa\" [\"aa\" \"cc\" \"aa\"]) => 4"
  (reduce (fn [acc item] (if (= target item) (+ (count item) acc) acc)) 0 options))

(defn score-all [targets options]
  "Calculates and sums up score for all targets in targets"
  (reduce #(+ %1 (score %2 options)) 0 targets))

(defn contigous [n xs]
  "Retrieve all the combinations of n contigous items from collection xs."
  (loop [shifts [xs]]
    (if (= n (count shifts))
      (apply map vector shifts)
      (let [tail (last shifts)
            drop-first (rest tail)]
        (recur (conj shifts drop-first))))))

(defn contigous-str [n s]
  "Retrieve all combinations of n contigous substrings of string s."
  (map #(apply str %) (contigous n (seq s))))

(defn matching [choices ^String target]
  "Return the substrings in choices that are present in target, or an empty list if none was found."
  (filter #(>= (.indexOf target ^String %) 0) choices))

(defn match [s target]
  "Return a matching score for string s, or its substrings, into target.
  The longer the matching substring the more points accumulated."
  (reduce + (take-while (partial < 0) (for [l (range 2 (inc (count s)))]
                                        (let [chunked (contigous-str l s)
                                              matching (matching chunked target)]
                                          (if-not (empty? matching) (score-all matching (contigous-str l target)) 0))))))

(defn best-score [s]
  "Calculate the maximum score for a n-length string s.
  Maximum achievable score is dependent on the lenght of s only"
  (let [l (count s)]
    (if (> l 1)
      (loop [sum 0 sublen 2]
        (let [score (+ sum (* sublen (- l (dec sublen))))] 
          (if (= sublen l)
            score
            (recur score (inc sublen)))))
      1)))

(defn match-% [s target]
  "Return a matching score for string s and its substrings into target
  as percentage of the possible maximum achievable."
  (let [match (match s target)
        best (best-score target)]
    (* (/ match best) 100.)))
