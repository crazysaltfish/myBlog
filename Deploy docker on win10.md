- 由于此次安装Docker废了一定时间，且考虑到之后再其他地方也会部署Docker，决定将安装经验记录下路以节省之后时间。
    其实安装过程的主要难点是针对Win10家庭版用户，Docker安装需要Hyper-V，而Win10家庭版没有；对于不想重装系统又想部署Docker的人，以下的经验将会帮助你解决问题。
  
    主要过程分4步：
    1. 安装Hyper-V
    2. 安装Containers Windows Feature
    3. 更改系统注册编辑器中的win10版本名称
    4. 更改Hyper-V虚拟文件存储地址
    
- 安装Hyper-V
    新建文件hyper-V.cmd将以下内容copy到里面：
    
    ``
      pushd "%~dp0"
      dir /b %SystemRoot%\servicing\Packages\*Hyper-V*.mum >hyper-v.txt
      for /f %%i in ('findstr /i . hyper-v.txt 2^>nul') do dism /online /norestart /add-package:"%SystemRoot%\servicing\Packages\%%i"
      del hyper-v.txt
      Dism /online /enable-feature /featurename:Microsoft-Hyper-V-All /LimitAccess /ALL
    ``
    
    保存后右键使用管理员权限运行，等待安装完成，输入Y重启电脑
    
- 安装Containers Windows Feature
    因为默认情况下windows容器窗口功能不可用，所以再安装之前先解决掉，以免卸载重装
    新建一个containers.bat，内容如下：
    
    ``
      pushd "%~dp0"
      dir /b %SystemRoot%\servicing\Packages\*containers*.mum >containers.txt
      for /f %%i in ('findstr /i . containers.txt 2^>nul') do dism /online /norestart /add-package:"%SystemRoot%\servicing\Packages\%%i"
      del containers.txt
      Dism /online /enable-feature /featurename:Containers -All /LimitAccess /ALL
      pause
    ``
    
    保存后右键使用管理员权限运行，同理等待运行完成，输入Y重启电脑
    更改系统注册编辑器中的win10版本名称
    由于在Docker安装时会检测当前Windows版本，可更改其版本名称 以满跳过海
    打开注册表编辑器，找到如下所示的 EditionID：
    ![1](https://img-blog.csdnimg.cn/20190330220903603.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2l0bmVyZA==,size_16,color_FFFFFF,t_70)
    把数值数据改成 Professional，瞒天过海
    运行安装 docker，成功运行
- 更改Hyper-V虚拟文件存储地址
    在安装好Docker之后，这一步是较为重要的，因为大多镜像都比较大，全堆在C盘会撑爆的。
    1. Win搜索打开Hyper-V，选中对应的虚拟机，选择"移动"，按提示将文件移动到其他盘。
    2. 但是这样还不够，你会发现每次重启docker，都会重新生成新的路径。 需要修改docker的配置文件，进入 %APPDATA%\Docker ， 修改 settings.json
    ![2](https://img2020.cnblogs.com/blog/1000786/202005/1000786-20200520155417164-1395397912.png)
  
