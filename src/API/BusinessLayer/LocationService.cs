using DataLinkLayer;
using DataLinkLayer.Models;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BusinessLayer
{
    public class LocationService
    {
        private readonly Context context;

        public LocationService(Context context)
        {
            this.context = context;
        }

        public List<Location> GetLocations()
        {
            return context.Locations.ToList();
        }

        public Location GetLocation(int id)
        {
            return context.Locations.Find(id); // this can be null if wrong id is given
        }

        public Location AddLocation(Location newLocation)
        {
            // check if newLocation is valid
            if (newLocation.Description == "" || newLocation.Hint1 == "" || newLocation.Hint2 == "" || newLocation.Naam == "" || newLocation.CrypticClue == "" || newLocation.Answer == "" || newLocation.Lat == null || newLocation.Long == null)
                return null;

            //check if location already exists
            var exists = context.Locations.Any(m => m.Naam.ToLower().Equals(newLocation.Naam.ToLower()) || m.Long.Equals(newLocation.Long) || m.Lat.Equals(newLocation.Lat) || m.Hint1.ToLower().Equals(newLocation.Hint1.ToLower()) || m.Hint2.ToLower().Equals(newLocation.Hint2.ToLower()) || m.CrypticClue.ToLower().Equals(newLocation.CrypticClue.ToLower()) || m.Description.ToLower().Equals(newLocation.Description.ToLower()) || m.Answer.ToLower().Equals(newLocation.Answer.ToLower()));
            if (!exists)
            {
                context.Locations.Add(newLocation);
                context.SaveChanges();
                return (newLocation);
            }
            else
            {
                return null;
            }
        }

        public bool DeleteLocation(int id)
        {
            var location = context.Locations.Find(id);
            if (location == null)
                return false;
            context.Locations.Remove(location);
            context.SaveChanges();
            return true;
            // returns false if there is no location with that id, returns true if the location is deleted
        }

        public bool UpdateLocation(Location updatedlocation)
        {

            ArrayList properties = new ArrayList();
            properties.Add(updatedlocation.CrypticClue);
            properties.Add(updatedlocation.Description);
            properties.Add(updatedlocation.Hint1);
            properties.Add(updatedlocation.Hint2);
            properties.Add(updatedlocation.Answer);
            properties.Add(updatedlocation.Naam);


            foreach (var item in properties)
            {

                if (item is string)
                {
                    System.Diagnostics.Debug.WriteLine("Item: " + item);
                    if (this.isEmpty(item.ToString()))
                        return false;

                }


            }

            if (isNull(updatedlocation.Lat))
                return false;
            if (isNull(updatedlocation.Long))
                return false;

            /*if (updatedlocation.Description == "" || updatedlocation.Hint1 == "" || updatedlocation.Hint2 == "" || updatedlocation.Naam == "" || updatedlocation.CrypticClue == "" || updatedlocation.Answer == "" || updatedlocation.Lat == null || updatedlocation.Long == null)
                return false;*/
            var location = context.Locations.Find(updatedlocation.ID);
            if (location == null)
                return false;
            else
            {
                location.Naam = updatedlocation.Naam;
                location.Lat = updatedlocation.Lat;
                location.Long = updatedlocation.Long;
                location.CrypticClue = updatedlocation.CrypticClue;
                location.Description = updatedlocation.Description;
                location.Answer = updatedlocation.Answer;
                location.Hint1 = updatedlocation.Hint1;
                location.Hint2 = updatedlocation.Hint2;
                context.SaveChanges();
                return true;
            }
        }


        public bool isEmpty(string input)
        {
            if (input == "")
                return true;
            return false;
        }

        public bool isNull(double? input)
        {
            if (input == null)
                return true;
            return false;
        }
    }
}
