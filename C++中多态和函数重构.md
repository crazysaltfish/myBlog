- c++中什么是多态：一般在使用指向父类的指针时会用到，会根据指针所指的对象，调用不同的同名函数。（注：此时父类的同名函数必须是虚函数，才能实现动态连接）

- 即使父类中函数不是虚函数，子类也能重写该函数，只是不能动态连接。

- c++中什么是函数重载：同一个类中，可以存在多个同名函数，但其参数不用。

```c++
#include <iostream> 
using namespace std;
 
class Shape {
   protected:
      int width, height;
   public:
      Shape( int a=0, int b=0)
      {
         width = a;
         height = b;
      }
      virtual int area()
      {
         cout << "Parent class area :" <<endl;
         return 0;
      }
};
class Rectangle: public Shape{
   public:
      Rectangle( int a=0, int b=0):Shape(a, b) { }
      int area ()
      { 
         cout << "Rectangle class area :" <<endl;
         return (width * height); 
      }
};
class Triangle: public Shape{
   public:
      Triangle( int a=0, int b=0):Shape(a, b) { }
      int area ()
      { 
         cout << "Triangle class area :" <<endl;
         return (width * height / 2); 
      }
};
// 程序的主函数
int main( )
{
   Shape *shape;
   Rectangle rec(10,7);
   Triangle  tri(10,5);
 
   // 存储矩形的地址
   shape = &rec;
   // 调用矩形的求面积函数 area
   // shape->area();
   rec.area();
 
   // 存储三角形的地址
   shape = &tri;
   // 调用三角形的求面积函数 area
   // shape->area();
   tri.area();
   
   return 0;
}
```
