(ns potter.core-test
  (:require [clojure.test :refer :all]
            [potter.core :refer :all]))

(deftest test-basics
  (is (== 0 (calculate-price [])))
	(is (== 8 (calculate-price [0])))
	(is (== 8 (calculate-price [1])))
	(is (== 8 (calculate-price [2])))
	(is (== 8 (calculate-price [3])))
	(is (== 8 (calculate-price [4])))
	(is (== 16 (calculate-price [0 0])))
	(is (== 24 (calculate-price [1 1 1]))))

(deftest test-simple-discounts
	(is (== (discount 2) (calculate-price [0 1])))
	(is (== (discount 3) (calculate-price [1 2 3])))
	(is (== (discount 4) (calculate-price [1 2 3 4])))
	(is (== (discount 5) (calculate-price [0 1 2 3 4]))))

(deftest test-several-discounts
  ; due to the scale of the numbers i have had to use 2.00 as the factor !?
  ; 51.2
  (is (== (* 2.00 (discount 4))
          (calculate-price [:a :a :b :b :c :c :d :e])))
  (is (== (* 2.00 (discount 4))
          (calculate-price [0 0 1 1 2 2 3 4])))
  (is (== (+ (discount 5) (discount 1))
          (calculate-price [0 1 1 2 3 4])))
  (is (== (* 4 (discount 2))
          (calculate-price [0 0 0 0 1 1 1 1])))
  (is (== (+ 8 (discount 2))
          (calculate-price [:a :a :b])))
  (is (== (* 2 (discount 2))
          (calculate-price [:a :a :b :b])))
  (is (== (+ (discount 4) (discount 2))
          (calculate-price [:a :b :c :d :a :c])))
  (is (== (+ (discount 5) (discount 1))
          (calculate-price [:a :b :c :d :e :b])))
  (is (== (+ (* 3 (discount 5)) (* 2 (discount 4)))
          (calculate-price [:a :a :a :a :a
                            :b :b :b :b :b
                            :c :c :c :c
                            :d :d :d :d :d
                            :e :e :e :e]))))

(deftest test-edge-cases
  ; 72.8
  (is (== (+ (* 2.0M (discount 4)) (discount 3))
          (calculate-price [0 0 0 1 1 1 2 2 2 3 4])))
  ; 78
  (is (== (+ (discount 5) (discount 4) (discount 2) (discount 1))
          (calculate-price [0 0 0 1 1 2 2 2 2 3 3 4])))
  ; 100
  (is (== (+ (* 3 (discount 4)) (discount 2) (discount 1))
          (calculate-price [0 1 1 2 2 2 3 3 3 3 4 4 4 4 4])))
  ; 141.6
  (is (== (+ (* 2 (discount 5)) (* 2 (discount 4)) (* 2 (discount 2)))
          (calculate-price [0 0 0 1 1 1 1 2 2 2 3 3 3 3 3 3 4 4 4 4 4 4])))
  ; 108
  (is (== (+ (* 3 (discount 4)) (discount 2) (* 2 (discount 1)))
          (calculate-price [0 0 0 1 1 1 1 2 2 2 3 3 3 3 3 3]))))
