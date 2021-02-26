(ns affinity.core
  (:require [clojure.string :as str]
            [clojure.core :refer :all]))

(defn split-by
  "Data-last str/split"
  [r s] (map str/lower-case (str/split s r)))

(defn squash
  "Create a list of hash-maps from a list of vectors of key-values"
  [xs] (reduce #(assoc {} (keyword %) %2) xs))

(defn merge-maps
  "Merge a list of hash-maps into a single hash-map"
  [xs] (reduce merge (map squash xs)))

(defn map-values
  "Apply a function to all values of a map"
  [f m] (into {} (for [[k v] m] [k (f v)])))

(defn get-by
  "Get a key-value from a map by key"
  [m k] (filter #(= (first %) (keyword k)) m))

(defn get-by-all
  "Get a list of key-values from a map by a vector of keys"
  [m ks] (apply hash-map (flatten (map (partial get-by m) ks))))

(def split-by-value (partial split-by #"\s+"))

(defn get-map-from-txt
  "Parses text file and return map of values"
  [parse-with, txt] (map-values parse-with (merge-maps (map split-by-value (str/split-lines txt)))))

;; ----

(defrecord Sentiment [score, comparative, tokens, words, positive, negative])

;; ----

(defn get-sentiment [txt]
  (def word-text-path "resources/AFINN-en-165.txt")
  (def word-text (slurp word-text-path))
  (def word-map (get-map-from-txt #(Integer/parseInt %) word-text))

  (def tokens (apply vector (split-by-value txt)))
  (def token-count (count tokens))
  (def matches (get-by-all word-map tokens))
  (def score (reduce + (vals matches)))
  (def comparative (/ score token-count))

  (def words (apply vector (map name (keys matches))))

  (def positive (apply vector (map name (keys (apply hash-map(flatten(filter #(> (second %) 0) matches)))))))
  (def negative (apply vector (map name (keys (apply hash-map(flatten(filter #(< (second %) 0) matches)))))))

  (def result (Sentiment. score comparative tokens words positive negative))
  result
)
