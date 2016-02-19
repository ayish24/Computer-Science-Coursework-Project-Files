(ns assignment3.core
  (:require [quil.core :as q]
            [clojure.java.io :as cj]))

;; Stores the message to be displayed on the window.
(def command-message (atom ""))

;; Stores the current pen-state.
(def pen-state (atom "down"))

;; Stores the turtle commands that are yet to be executed.
(def todo-commands (atom []))

;; Stores the turtle commands that are executed already.
(def command-history (atom []))

;; Multi-method used to execute the following turtle commands : move, turn, pen up , pen down.
(defmulti execute :command)

;; Sets the pen-state to the given value. Possible values are: up,down.
(defmethod execute "pen" [x]
  (reset! command-message (str "pen " (:value x)))
  (reset! pen-state (:value x)))

;; Moves the turtle the given amount in the current direction.
(defmethod execute "move" [x]
  (reset! command-message (str "move " (:value x)))
  (when (= @pen-state "down")
    (q/line 0 0 (read-string (:value x)) 0))
  (q/translate (read-string (:value x)) 0))

;; Turns the direction of the turtle the given angle in degrees.
(defmethod execute "turn" [x]
  (let [val (read-string (:value x))]
    (reset! command-message (str "turn " val))
    (q/rotate (q/radians val))))

(defn run-commands
  "Execute the turtle commands from the @command-history"
  []
  (q/background 240)
  (reset! pen-state "down")
  (doseq [command @command-history]
    (execute command)))

(defn do-all-commands
  "Executes all the turtle commands from the list"
  []
  (reset! command-history (into [] (concat @command-history @todo-commands)))
  (reset! todo-commands '())
  (run-commands))

(defn undo-command
  "Undo the previously executed turtle command"
  []
  (let [last-command (last @command-history)]
    (swap! command-history pop)
    (swap! todo-commands conj last-command)))

(defn do-next-command
  "Executes the next turtle command from the list"
  []
  (when (seq @todo-commands)
    (let [command (peek @todo-commands)]
      (swap! todo-commands pop)
      (swap! command-history conj command))))

(defn keyboard-action []
  (let [key (q/key-as-keyword)]
    (cond
     (= key :left)(undo-command)
     (= key :right)(do-next-command)
     (= key :r)(do-all-commands)
     (= key :R)(do-all-commands))))

;; The following 3 functions , init-command-list, read-file and convert-to-map are used to read turtle program
;; It initializes the commands to todo-commands atom.
(defn convert-to-map [ks vs]
  (let [[key1 key2] ks
        [val1 val2] vs]
    {key1 val1 key2 val2}))

(defn get-lines
  "Store all the lines from the file in a list"
  [filename]
  (with-open [r (cj/reader (cj/resource filename))]
    (doall (line-seq r))))

(defn split-column
  "Split the columns in every line and store as list of vectors"
  [file-name]
  (map #(clojure.string/split % #" ") (get-lines file-name)))

(defn read-file [filename]
  (map (partial convert-to-map [:command :value]) (split-column filename)))

(defn init-command-list [filename]
  (reset! todo-commands (reverse (into '() (read-file filename)))))

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  ; Initialize todo-commands list from the text file
  (init-command-list "turtle-commands.txt"))

(defn draw-state []
  ; Clear the sketch by filling it with light-grey color.
  (q/background 240)
  (q/fill 0)
  ; Move origin point to the center of the sketch.
  (q/translate
    (/ (q/width) 2)
    (/ (q/height) 2))
  ; Execute commands from the command-list.
  (run-commands)
  (q/reset-matrix)
  ; Display the command that is just executed.
  (q/text @command-message 10 30))

(q/defsketch assignment3
  :title "Turtle Graphics"
  :size [500 500]
  ; setup function called only once, during sketch initialization.
  :setup setup
  :draw draw-state
  :key-pressed keyboard-action
  :features [:keep-on-top])

