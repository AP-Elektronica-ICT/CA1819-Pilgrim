# Pilgrim guide

The application has 3 distinct cores:

- Mobile application
    - Pilgrim (native android app)
    - Pilgrim AR (vuforia app in Unity)
- Web-API hosted on azure
- Angular page to manage the web API

Post-development the angular page can be used to add, edit or delete Locations from the API. This makes it easy to maintain and it can be done without any programming knowledge.

## Setup

Open a CMD window in the source folder of angular ```../CA1819-Pilgrim/src/angular```.  
Once this command window is open we can install all the needed node modules by running ```npm i```.  
When this is finished we can start the angular application by entering the following command in the CMD-window: ```ng serve```. After a while the following message should appear:

![cmd](https://i.imgur.com/lp4OFLk.png)

This indicates that the angular app is up and running, you should now navigate to [localhost:4200](https://localhost:4200).

## Adding locations

There are 2 steps in the process of adding a location to the game.

1. Adding the location to the API
2. Adding the location to vuforia for the AR portion of the APP

### 1. API

To add a location to the API you will need to go to the angular webpage.  
Once you are here you can click on **ADD LOCATION**. This will open up a window in which you can configure your now location.  
  
  <div style="text-align:center"><img src ="https://i.imgur.com/WlvLsmP.png" /></div>

First off you can choose a picture for the location (at the moment only png images are supported).  
Next up you have to enter all the information, important to know is that none of the fields can be duplicates of other locations.  
When All of your information is entered you can press the Add Location button to send it to the API.

### 2. Vuforia

To use Vuforia you have to follow a couple of steps.

1. Download Unity. 
2. surf to https://developer.vuforia.com/target-manager, Login or create an account.
3. Click on "get developement key"
4. Click on " add database"
5. Upload images in database.
6. In Unity paste the key in "Vuforia Configuration"
7. In Vuforia portal click on " download database" this will automatically open and import it in Unity. 
8. Add an AR camera in unity. 
9. Add an imageobject in the camera + select which image.
10. Add Quad to image. 
11. Make a Material + drag the image you want to augment from your computer onto the material. 
12. Drag the Material onto the Quad.
13. Your phone will now be able to augment the second image after you build the application.

14. We found a Tutorial on making images augmentable that will make all the above steps more comprehensible: https://www.youtube.com/watch?v=MtiUx_szKbI

## Edit locations

Changing the information of locations is very similar to adding new ones. Instead of pressing the **ADD LOCATION** button we will click on the little pen next to an existing location. This will open the same window as if we were adding locations but filled in with the existing information of the selected location.  
You can now edit this information and save it by clicking **EDIT LOCATION**

## Deleting locations

Deleting locations is very easy, just click on the little cross next to the location you want to delete.

**WARNING** Deleting locations is final, a deleted location can only be manually readded.
