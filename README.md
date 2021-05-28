# JavaFXGame

<h2>How to play</h2>

There are five levels exists at this game. You need to earn highest possible Score by destorying boxes. Every clicked box will effect its nearby boxes. The scoring system is like this:



Some screenshots:

![image](https://user-images.githubusercontent.com/61292962/119404074-f7746600-bce7-11eb-839e-b864c87f310b.png)

![image](https://user-images.githubusercontent.com/61292962/119404403-6baf0980-bce8-11eb-8f6c-f481c4c16f21.png)

![image](https://user-images.githubusercontent.com/61292962/119404455-7ff30680-bce8-11eb-81b6-281c11738970.png)

![image](https://user-images.githubusercontent.com/61292962/119404523-9731f400-bce8-11eb-9329-7dd1cc4f6134.png)



<h2>How to run this project ?</h2>

1. This project uses external libraries such as JavaFX. Firstly you have to download JavaFX libraries.

https://gluonhq.com/products/javafx/ 

I recommend you to extract files to your project workspace.

2. After that you need to import this project to your Workspace.
3. After importing process is done you need to add JavaFX libraries to this project.

After importing the project you'll encounter these errors due to missing JavaFX libraries:
![image](https://user-images.githubusercontent.com/61292962/119402815-2558ab00-bce6-11eb-8700-01e21c752c50.png)

Firstly you need to delete these imports by selecting all of them and pressing remove at the right panel
![image](https://user-images.githubusercontent.com/61292962/119403019-6d77cd80-bce6-11eb-9dd7-7cea0e3f97b4.png)

After that we just need to find and add downloaded libraries

![image](https://user-images.githubusercontent.com/61292962/119403287-c34c7580-bce6-11eb-8f05-4cd5595e569b.png)

Just click to add external jars button

![image](https://user-images.githubusercontent.com/61292962/119403369-e37c3480-bce6-11eb-9f7d-424a3ab574e9.png)

Select all of these jar files then press open. After that just press apply and close.

4. After adding libraries ve need to add some carguments to the compiler 
Simply Run->Running Configurations-> Arguments then simply print this to the VM arguments:
--module-path "\path\to\javafx-sdk-12.0.1\lib" --add-modules javafx.controls,javafx.fxml

After that you can simply run the project :)




<strong>Note: This my first project on github. I know there are a lot of errors and mistaks exist both project and readme file. I'll be gratefull if you let me know about my mistakes. Thanks :)</strong>.

