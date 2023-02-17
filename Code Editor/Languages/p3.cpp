
#include <iostream>
using namespace std;

class Complex
{
    private:
    int b;
    protected:
    int a;
    public:
    int getData()
    {
        return a;
    }
    void setData(int a)
    {
        this->a= a;
    }

};

class Simple : public Complex
{
    public:
    // void setB(int b)
    // {
    //     this->b=b;
    // }
    int printData()
    {
       return a; 
    }
};
int main()
{
    Simple s;
    s.setData(10);
    cout<<s.printData()<<endl;
    return 0;
}