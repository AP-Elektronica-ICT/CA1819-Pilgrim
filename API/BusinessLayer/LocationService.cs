using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using DataLinkLayer;
using DataLinkLayer.Models;
using Newtonsoft.Json;

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


            var exists = context.Locations.Any(m => m.Naam.ToLower().Equals(newLocation.Naam.ToLower()) || m.Long.Equals(newLocation.Long) || m.Lat.Equals(newLocation.Lat) || m.Hint1.ToLower().Equals(newLocation.Hint1.ToLower()) || m.Hint2.ToLower().Equals(newLocation.Hint2.ToLower()) || m.CrypticClue.ToLower().Equals(newLocation.CrypticClue.ToLower()) || m.Description.ToLower().Equals(newLocation.Description.ToLower()) || m.Answer.ToLower().Equals(newLocation.Answer.ToLower()));
            if (!exists)
            {
                context.Locations.Add(newLocation);
                context.SaveChanges();
                return (newLocation);
            }
            else {
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

        }
    }
}
