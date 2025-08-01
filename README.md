# 泳池捉迷藏定时提醒器 | Pool Hide-and-Seek Timer App

专为泳池捉迷藏游戏设计的定时提醒应用，帮助"抓捕者"在规定时间内获得视觉提示机会。

*A timer reminder app specifically designed for pool hide-and-seek games, helping the "seeker" get visual hint opportunities at specified intervals.*

## 游戏规则 | Game Rules

### 🏊‍♂️ 泳池捉迷藏玩法 | Pool Hide-and-Seek Gameplay

**中文说明：**
1. **抓捕者准备**：戴上眼镜或眼罩，完全遮住双眼，无法看到任何东西
2. **游戏目标**：在泳池中触碰到任何一个其他玩家即可获胜
3. **视觉提示机会**：当APP发出提示音时，抓捕者可以摘下眼镜观察周围环境和人员位置
4. **提示时间**：每次提示音持续一定时间（默认10秒），期间可以自由观察
5. **游戏继续**：提示时间结束后重新戴上眼镜，继续寻找其他玩家

**English Description:**
1. **Seeker Setup**: Wear goggles or blindfold, completely covering the eyes, unable to see anything
2. **Game Objective**: Touch any other player in the pool to win
3. **Visual Hint Opportunity**: When the app sounds an alert, the seeker can remove the goggles to observe the surroundings and people's positions
4. **Hint Duration**: Each alert lasts for a set time (default 10 seconds), during which free observation is allowed
5. **Game Continues**: After the hint time ends, put the goggles back on and continue searching for other players

### 🎯 APP功能说明 | App Features

**中文：**
- **定时提醒**：每隔设定时间（默认60秒）自动发出提示音
- **观察时间**：提示音持续时间（默认10秒）内可以摘下眼镜观察
- **声音提醒**：清晰的提示音确保在水中也能听到
- **时间可调**：根据游戏难度调整提醒间隔和观察时间

**English:**
- **Timed Reminders**: Automatically sounds alerts at set intervals (default 60 seconds)
- **Observation Time**: Can remove goggles during alert duration (default 10 seconds)
- **Audio Alerts**: Clear notification sounds audible even in water
- **Adjustable Timing**: Customize reminder intervals and observation time based on game difficulty

## 功能特性 | Features

### 🕒 定时提醒 | Timed Reminders
**中文：**
- **可配置提醒间隔**：默认60秒，支持5-300秒调节（5秒步长）
- **可配置观察时间**：默认10秒，支持1-60秒调节
- **实时倒计时显示**：大字体圆形计时器，清晰显示距离下次"偷看"机会的剩余时间
- **自动循环**：观察时间结束后自动重新开始计时

**English:**
- **Configurable Reminder Intervals**: Default 60 seconds, adjustable from 5-300 seconds (5-second increments)
- **Configurable Observation Time**: Default 10 seconds, adjustable from 1-60 seconds
- **Real-time Countdown Display**: Large font circular timer clearly showing time remaining until next "peek" opportunity
- **Auto Loop**: Automatically restarts timing after observation time ends

### 🎵 声音提醒 | Audio Alerts
**中文：**
- **系统提示音选择**：可从系统通知音中选择适合水下环境的清晰提示音
- **提示音试听**：选择前可以试听3秒预览效果，确保音量和清晰度
- **声音播放控制**：支持观察期间的声音循环播放，确保不会错过

**English:**
- **System Ringtone Selection**: Choose clear notification sounds suitable for underwater environments
- **Sound Preview**: 3-second preview before selection to ensure volume and clarity
- **Audio Playback Control**: Supports looping during observation period to ensure alerts aren't missed

### 🎨 用户界面 | User Interface
**中文：**
- **Material Design设计**：现代化的界面设计，即使在泳池边也易于操作
- **直观的控制界面**：简单的+/-按钮调节参数，戴着泳镜也能轻松使用
- **状态显示**：实时显示当前状态（等待开始、游戏进行中、观察时间等）
- **圆形进度显示**：美观的倒计时展示，一目了然

**English:**
- **Material Design**: Modern interface design, easy to operate even poolside
- **Intuitive Controls**: Simple +/- buttons for parameter adjustment, easy to use even with swim goggles
- **Status Display**: Real-time status indicators (waiting to start, game in progress, observation time, etc.)
- **Circular Progress Display**: Beautiful countdown visualization at a glance

### 💾 设置保存 | Settings Persistence
**中文：**
- **自动保存设置**：所有游戏配置自动保存，下次游戏时恢复
- **游戏时保护**：定时器运行时锁定设置，防止游戏中误操作

**English:**
- **Auto-save Settings**: All game configurations automatically saved and restored for next game
- **Game-time Protection**: Settings locked during timer operation to prevent accidental changes during gameplay

## 技术实现

- **开发语言**：Kotlin
- **最低Android版本**：API 21 (Android 5.0)
- **目标Android版本**：API 32 (Android 12)
- **UI框架**：Material Components for Android
- **数据绑定**：ViewBinding
- **本地存储**：SharedPreferences

### 主要技术组件
- `CountDownTimer`：精确的倒计时实现
- `MediaPlayer`：音频播放管理
- `RingtoneManager`：系统铃声访问
- `CardView`：现代化卡片布局
- `ConstraintLayout`：灵活的响应式布局

## 安装和运行

### 开发环境要求
- Android Studio Arctic Fox 或更新版本
- JDK 11 或更新版本
- Android SDK API 32

### 编译和安装
```bash
# 编译Debug版本
./gradlew assembleDebug

# 安装到连接的设备
./gradlew installDebug
```

### 权限说明
应用需要以下权限：
- `WAKE_LOCK`：保持设备唤醒以确保定时器准确运行
- `VIBRATE`：提供振动反馈（预留功能）
- `FOREGROUND_SERVICE`：后台服务支持（预留功能）

## 使用说明 | How to Use

### 🎮 游戏设置 | Game Setup

**中文：**
1. **设置提醒间隔**：使用提醒间隔区域的+/-按钮调整时间（5-300秒）
   - 建议新手：90-120秒（更频繁的观察机会）
   - 建议高手：60秒或更少（增加挑战难度）

2. **设置观察时间**：使用观察时间区域的+/-按钮调整（1-60秒）
   - 建议设置：10-15秒（足够观察但不会太长）

3. **选择提示音**：点击"选择"按钮从系统铃声中选择清晰的提示音
   - 建议选择音量较大、音调较高的提示音
   - 点击"试听"确保在泳池环境中也能清楚听到

4. **开始游戏**：
   - 抓捕者戴好眼镜或眼罩
   - 点击"开始"按钮启动定时器
   - 将手机放在泳池边的安全位置

**English:**
1. **Set Reminder Interval**: Use +/- buttons in the reminder interval section (5-300 seconds)
   - Beginner recommendation: 90-120 seconds (more frequent observation opportunities)
   - Expert recommendation: 60 seconds or less (increased challenge)

2. **Set Observation Time**: Use +/- buttons in the observation duration section (1-60 seconds)
   - Recommended setting: 10-15 seconds (enough time to observe without being too long)

3. **Choose Alert Sound**: Click "Select" to choose a clear notification sound from system ringtones
   - Recommend louder, higher-pitched alert sounds
   - Click "Preview" to ensure it's clearly audible in pool environment

4. **Start Game**:
   - Seeker puts on goggles or blindfold
   - Click "Start" button to begin timer
   - Place phone in a safe position by the pool

### 🏊‍♂️ 游戏进行 | During Gameplay

**中文：**
1. **等待提示音**：抓捕者在泳池中寻找其他玩家，等待APP发出提示音
2. **观察时间**：听到提示音后立即摘下眼镜，快速观察周围环境和人员位置
3. **重新开始**：观察时间结束后重新戴上眼镜，继续游戏
4. **游戏结束**：触碰到任何玩家即可获胜，点击"停止"按钮结束游戏

**English:**
1. **Wait for Alert**: Seeker searches for other players in the pool while waiting for app alert
2. **Observation Time**: Immediately remove goggles when alert sounds, quickly observe surroundings and player positions
3. **Resume**: Put goggles back on after observation time ends, continue game
4. **Game Over**: Win by touching any player, click "Stop" button to end game

### ⚠️ 安全提醒 | Safety Reminders

**中文：**
- 确保手机放置在防水安全的位置
- 泳池边保持干燥，避免滑倒
- 游戏前确认所有参与者都会游泳
- 避免在深水区进行激烈的追逐

**English:**
- Ensure phone is placed in a waterproof, safe location
- Keep pool area dry to prevent slipping
- Confirm all participants can swim before starting
- Avoid intense chasing in deep water areas

## 项目结构

```
app/
├── src/main/
│   ├── java/com/rjwang/zhuomicang/
│   │   └── MainActivity.kt          # 主活动，包含所有业务逻辑
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml    # 主界面布局
│   │   ├── values/
│   │   │   ├── strings.xml          # 字符串资源
│   │   │   └── dimens.xml           # 尺寸定义
│   │   └── ...
│   └── AndroidManifest.xml         # 应用配置文件
├── build.gradle                    # 模块构建配置
└── ...
```

## 版本历史

### v1.0 (当前版本)
- ✅ 基础定时提醒功能
- ✅ 可配置时间参数
- ✅ 系统提示音选择
- ✅ 设置自动保存
- ✅ Material Design界面

## 计划功能

- [ ] 振动提醒选项
- [ ] 后台运行支持
- [ ] 多种提醒模式
- [ ] 统计功能
- [ ] 深色主题支持

## 开发者

项目由 GitHub Copilot 协助开发，专注于简洁实用的用户体验。

## 许可证

本项目仅供学习和个人使用。
