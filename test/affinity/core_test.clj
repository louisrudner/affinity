(ns affinity.core-test
  (:require [midje.sweet :refer [fact] :rename {fact expect}]
            [affinity.core :refer :all]))

(expect (split-by #"\s+" "Test   text") => ["test", "text"])
(expect (squash ["a" "1"]) => {:a "1"})
(expect (merge-maps '(["a" "1"] ["b" "2"])) => {:a "1" :b "2"})
(expect (map-values inc {:a 1 :b 2}) => {:a 2 :b 3})
(expect (get-by {:a 1 :b 2} "a") => '([:a 1]))
(expect (get-by-all {:a 1 :b 2 :c 2} ["a" "b"]) => {:a 1 :b 2})
(expect (get-map-from-txt #(Integer/parseInt %) "hello 1\ngoodbye   2\n") => {:hello 1 :goodbye 2})

(expect (get-sentiment "I love cats but I am allergic to them") =>
        {:comparative 1/9
         :negative ["allergic"]
         :positive ["love"]
         :score 1
         :tokens ["i" "love" "cats" "but" "i" "am" "allergic" "to" "them"]
         :words ["allergic" "love"]})
