;;
;; Implementation of the String-Calculator-Kata
;; @see http://osherove.com/tdd-kata-1/
;;
(ns string-calculator.core
  (:import [java.lang Integer])
  (:use [clojure.string :only [split trim join]])
  (:gen-class))

(defn- escape-sonderzeichen [s]
  (clojure.string/replace s #"[\*\?\%]" #(str \\ %1)))

(defn- parse-zahl [s]
  (if (= s "")
    0
    (Integer/valueOf (trim s))))

(defn- hat-separator? [s]
  (.startsWith s "//"))

(defn- parse-separator [s]
  (->> (split (second (re-find #"(?s)^//[\[]{0,1}(.*?)[\]]{0,1}\n" s)) #"\]\[")
       (join "|")
       (str ",|\n|")
       (escape-sonderzeichen)))

(defn- zahlen-vor-separator [s]
  (.substring s (inc (.indexOf s "\n"))))

(defn- trenne-separator-und-zahlen [s]
  (if (hat-separator? s)
    [(zahlen-vor-separator s) (re-pattern (parse-separator s))]
    [s #"[,\n]"]))

(defn melde-negative-zahlen [negative-zahlen]
  (throw (Exception.
          (str "Negative Zahlen sind nicht erlaubt. Fehlerhafte Zahl"
               (when (> (count negative-zahlen) 1) "en")
               ": "
               (join ", " negative-zahlen)))))

(defn kleiner-gleich-1000? [zahl] (<= zahl 1000))

(defn add [input-string]
  (let [zahlen (->> (trenne-separator-und-zahlen input-string)
                    (apply split)
                    (map parse-zahl)
                    (filter kleiner-gleich-1000?))
        negative-zahlen (filter neg? zahlen)]
    (if (seq negative-zahlen)
      (melde-negative-zahlen negative-zahlen)
      (reduce + zahlen))))
