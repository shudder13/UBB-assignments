# General
Several classes can be defined in a .java file, but not more than one can be public.  
Default access modifier - the data members, classes or methods which are not declared using any access modifiers are accessible only within the same package.  
If a data member is not initialized explicitly, it will have the implicit value corresponding to its type.  
Several methods with the same name, but different signatures can be defined in a class. (signature = the number, type and order of formal parameters)  
The transmission of parameters is done by value. Their value is copied onto the stack. For object type parameters and for arrays, this value is a reference. The information in the object or table can be modified.  
Instance variables cannot be used in static methods (compilation error).  
**Autoboxing** = the automatic conversion that the Java compiler makes between the primitive types and their corresponding object wrapper classes.  

# Constructors
The constructor must have the same name as the class (case sensitive).  
Constructor has no return type.  
If no constructor is defined in a class, the compiler will automatically generate an implicit constructor - default constructor, with the public access modifier.  
A constructor can call another constructor of the same class. The call to the other constructor must be the first statement in the calling constructor.  
Two different constructors cannot be called.  
A constructor cannot be called from inside other methods.  
There is no destructor in Java. The memory is deallocated by the garbage collector.  

# Inheritance
A class can inherit not more than one class. There is no multiple inheritance in Java.  
If in the superclass there is a constructor without parameters (default or not), the compiler automatically introduces a call to that constructor as the first instruction.  
If the base class has no constructor without parameters, a constructor with parameters from the superclass must be explicitly called in the constructor of the subclasses.  
The first statement in a constructor is either a call to another constructor of the same class, or a call to a constructor from the direct superclass.  
In Java, any class is derived from Object class. All objects inherit the methods of this class: toString, equals, hashCode, getClass, clone, finalize.  

# Overriding and overloading
We cannot override static methods. (only instance methods)  
The overriden method in the derived class can have the return type as a subtype of the one returned by the method in the base class.
In Java, any method is implicitly virtual.  
Overriding = same name, same signature  
Overloading = same name, different signatures  

# Final keyword
Final methods cannot be overridden in derived classes.  
Final classes cannot be inherited (they are leaves in the class hierarchy tree)  
Final data members, local variables, arguments = constants  
Static and final constants must be initialized upon declaration.  

# Abstract classes
Rule: Don't use inheritance just to reuse the code of a superclass. Favor composition instead of inheritance!  
An abstract method is declared, but not defined in the class in which it was declared.  
An abstract class is a class that can contain abstract methods (but it is not mandatory).  
An abstract class cannot be instantiated.  
If a class has at least one abstract method, then it must be declared abstract.  
A class can be declared abstract without having abstract methods.  
If a class inherits an abstract class and does not define all the abstract methods of the base class, it must also be declared abstract.  

# Interfaces
An interface contains declarations of abstract methods (undefined), default (defined) methods, static methods and data members.  
Any data member declared in an interface is by default public, static and final.  
All methods declared in an interface are public by default.  
An interface may not contain any declaration of methods or data members.  
An interface can extend another interface and add new methods.  
An interface can extend multiple interfaces.  
If at least one abstract method is not defined in the class, then the class must be declared abstract.  
A class can inherit at most one class, but can implement multiple interfaces.  

# Lambda expressions
A lambda expression is a short block of code which takes in parameters and returns a value. Lambda expressions are similar to methods, but they do not need a name and they can be implemented right in the body of a method.  
Java's Consumer interface can be used to store a lambda expression in a variable.  
We can refer to static, instance and local variables in lambda expressions; local variables are final by default (we cannot modify their values).  

# Functional interfaces
A functional interface is an interface that contains exactly one abstract method (@FunctionalInterface annotation).  
## Built-in functional interfaces  
**Predicate**: accepts one argument and returns a boolean  
**Consumer**: accepts one argument, produces a result and does not return any value  
**Function**: accepts one argument and returns a value  
**UnaryOperator**: extends Function; accepts one argument and returns a result of the same type of its argument
**BiFunction**: accepts two arguments and returns a value  
**BinaryOperator**: extends BiFunction; accepts two arguments and returns a result with a specific generic type
**Supplier**: does not take in any argument and returns a result with a specific generic type  
**Comparator**: a comparator object is capable of comparing two objects of the same class  

# Generics
Generic types are used to parameterize data types, parameters being also data types.  
When creating instances of generic classes, primitive types cannot be used (int, byte, char, float, double), but associated classes (Integer, Byte, Character, Float, Double).  
To create an array in a generic class, the array will have the Object type and when an element is requested, it will be cast explicitly.  
The generic type of a generic method can be different from the generic type of the class in which the generic method is declared.  
Generic attributes of generic classes cannot be static.  
**Erasure**: In Java, a new class is not created for each instance of a generic class (with a different type). When compiling, the compiler "erases" information about the generic type and replaces each generic type variable with the type's upper bound (usually Object), and where needed inserts an explicit cast to the generic type (compatibility reasons).  
If ChildType is a subtype (descendant class or subinterface) of ParentType, then a generic structure GenericStructure\<ChildType> is not a subtype of GenericStructure\<ParentType>.  

# Hash vs. Tree vs. LinkedHash Map or Set
**Hash**: stores its elements in a hash table; it is the most efficient implementation, but we have no guarantees on the order of traversal.  
**Tree**: stores its elements in a red-black tree; elements are ordered based on their values; slower implementation.  
**LinkedHash**: is implemented as a hash table. The difference between Hash and LinkedHash is that LinkedHash maintaints a doubly linked list over all its elements. Therefore, they remain in the order in which they were inserted.  

# Rules
"Program to an interface, not to an implementation."  
"Hide and abstract as much as possible."  
"Favor object composition over inheritance."  
"Minimize relationships among objects and organize related objects in packages."  
