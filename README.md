# FTC Program Template
## Introduction
This template provides a program structure for FTC (FIRST Tech Challenge) robot programming using a State mechanism.
By utilizing the State mechanism, you can centrally manage the robot's state, making data sharing and control among various components more efficient.
<br><br>
このテンプレートは、FTC（FIRST Tech Challenge）ロボットプログラミングにおいて、Stateを用いたプログラムの構造を提供します。Stateを使用することで、ロボットの状態を一元管理し、各コンポーネント間のデータ共有と制御を効率的に行うことができます。

## Project Structure
```
├── Main.java
├── component
│     └── Component.java
├── state
│     └── State.java
└── subClass
    ├── Const.java
    └── Util.java
```
- Main.java: The main OpMode of the robot, handling initialization and loop processing.
- component/: Contains component classes that implement various functionalities of the robot.
  - Component.java: An interface that all components should implement.
- state/: Contains State.java, which manages the robot's state.
- subClass/: Contains auxiliary classes.
  - Const.java: Defines constants.
  - Util.java: Provides utility functions.
    <br><br>
- Main.java: ロボットのメインOpMode。初期化とループ処理を行います。
- component/: ロボットの各機能を実装するコンポーネントクラスを格納します。
  - Component.java: すべてのコンポーネントが実装すべきインターフェース。
- state/: ロボットの状態を管理するState.javaを格納します。
- subClass/: 補助的なクラスを格納します。
  - Const.java: 定数を定義します。
  - Util.java: ユーティリティ関数を提供します。

## How it works
The State mechanism is a system that centrally manages all state information, such as sensor data, controller inputs, and operating modes of the robot.
This allows each component to share data through a common State object, improving code readability and maintainability.

Each component implements the Component interface and interacts with the State through the following two methods:
- readSensors(State state): Reads data from sensors and updates the State.
- applyState(State state): Controls the hardware based on the information in the State.

Stateシステムは、ロボットのセンサー情報、コントローラーの入力、動作モードなど、すべての状態情報を一元的に管理する仕組みです。
これにより、各コンポーネントが共通のStateオブジェクトを介してデータをやり取りでき、コードの可読性と保守性が向上します。

各コンポーネントはComponentインターフェースを実装し、以下の2つのメソッドを通じてStateを操作します。
- readSensors(State state): センサーからのデータを読み取り、Stateに反映します。
- applyState(State state): Stateの情報に基づいてハードウェアを制御します。