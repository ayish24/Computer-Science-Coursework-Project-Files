;;;; ASSIGNMENT # 2

;;; This section of code contains functions that solves the following problems:
;;;   1. Divisors of an integer.
;;;   2. Abundant Number.
;;;   3. Abundant Number Series.
;;;   4. Pattern Count.
;;;   5. Most Frequent Word.
;;;   6. Find clumps.
;;;   7. File Parsing.


;; Problem 1 - Divisors of an integer.

;; Returns the sequence of divisors of the given positive integer N.
(defn divisors [N]
  (filter
   (comp zero? (partial rem N))
   (range 1 (inc N))))


;; Problem 2 - An abundant number

;; Returns the sum of the proper divisors of the number minus twice the number itself.
(defn abundance [n]
  (let[div (divisors n)
       sum-divisors(apply + div )
       twice-n (* 2 n)]
    (- sum-divisors twice-n)))


;; Problem 3 - Abundant number series

;; Returns the sum of the proper divisors of the number minus the number itself.
(defn abundant-num? [n]
  (let [result (abundance n)]
    (if (> result 0)
      n)))

;; Finds a series of abundant number within the given range.
(defn find-abundant-series [num-range]
  (let[temp-series(into[]
                       (for [ r (range num-range)]
                            (abundant-num? r)))]
      (remove nil? temp-series)))


;; List of all the abundant numbers less than 300.
(find-abundant-series 300)


;; Problem 4 - Pattern counting

;; Returns number of times the pattern occurs in the text.
(defn pattern-count
  [text pattern]
  (let [pattern-length (count pattern)
        pattern-sequence (seq pattern)]
    (loop [pattern-counter 0
           rem-text text]
      (if (< (count rem-text) pattern-length)
          pattern-counter
         (let [text-match? (= (take pattern-length rem-text) pattern-sequence)]
           (recur
            (if text-match?
              (inc pattern-counter)
              pattern-counter)
            (rest rem-text)))))))



;; Problem 5 - Most frequent word

;; Returns the function which contains the partitioned words of length 'n' from the given text.
(defn partition-text
  [text n]
  (map #(apply str %) (partition n 1 text)))

;; Returns the most frequent word of length 'n' that appears in the text.
(defn most-frequent-word
  [text n]
  (->> (partition-text text n)
       (frequencies)
       (sort-by val)
       (reverse)
       (group-by val)
       (first)
       (second)
       (map first)))


;; Problem 6 - Find clumps

;; Returns a sequence of strings of length k that appears at least t times in text.
(defn t-frequent-string
  [k t text]
  (->> (partition-text text k)
       (frequencies)
       (filter #(<= t (second %)))
       (map first)))

;; Returns a sequence of strings of length k that forms a (L,t) clump in text.
(defn find-clumps
  [text k L t]
  (let [L-string (partition L 1 text)]
    (->> L-string
         (map #(t-frequent-string k t %))
         (map set)
         (apply clojure.set/union)
         (seq))))


;; Problem 7 - Parsing tab separated file - weather.dat
;; Without using any Parsing Clojure libraries.

;; Returns a sequence of integer(s) till it encounters a letter
(defn strip-number [s] (Integer.(re-find #"\d+" s)))

;; Transfer column data into map
(defn col-data-to-map
  [line]
  (let [tokens (clojure.string/split line #"\s+")]
    {:Dy (nth tokens 0)
     :MxT (strip-number (nth tokens 1))
     :MnT (strip-number (nth tokens 2))
     }))

;; Transform lines to sequence of maps
(defn lines-to-maps [lines] (map col-data-to-map lines))

;; Calculate temperature spread for given item.
(defn spread [item] (- (:MxT item) (:MnT item)))

;; Lazily load data from file.
(defn load-data
  [filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (lines-to-maps
     (filter
      (fn [item] (re-find #"^\s*\d+" item))
      (reduce conj [] (line-seq rdr))))))

;; Returns day number, of the day that has the largest temperature spread.
(defn maximum-spread
  [file-path]
  (let [items (load-data file-path)
        max-spread-info (reduce
                         (fn [item1, item2]
                           (if(> (spread item1) (spread item2))
                             item1
                             item2))
                         items)]
    (:Dy max-spread-info)))















