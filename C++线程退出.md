pthread.exit();

是显示的退出当前线程，不会影响其他线程。

在需要使用多线程时，main线程的退出应该使用pthread.exit(0);

不用再使用return 0; 

exit(0);是退出进程 相当于return 0; 
