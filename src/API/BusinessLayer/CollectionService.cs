using DataLinkLayer;
using DataLinkLayer.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using Microsoft.EntityFrameworkCore;

namespace BusinessLayer
{
    public class CollectionService
    {
        private readonly Context context;

        public CollectionService(Context context)
        {
            this.context = context;
        }

        public Collection GetCollection(string id)
        {
            var collection = context.Collections.Include(r => r.Locations).FirstOrDefault(r => r.FireBaseID == id);
            collection.Locations = new List<Location>();
            var collectionLocations = context.CollectionLocation.Where(r => r.CollectionID == collection.ID);
            foreach (CollectionLocation i in collectionLocations)
            {
                var temp = context.Locations.Find(i.LocationID);
                if (temp.Image != null)
                {
                    temp.base64 = Convert.ToBase64String(temp.Image);
                }
                else
                {
                    temp.base64 = "";
                }
                collection.Locations.Add(temp);
            }

            return collection;
            
        }

        public bool AddLocationToCollection(string id, Location _location)
        {
            var collection = context.Collections.FirstOrDefault(r => r.FireBaseID == id);
            
            

           if (_location == null || collection == null)
                return false;
           
            if(context.CollectionLocation.Where(r => r.CollectionID == collection.ID && r.LocationID == _location.ID).ToList().Count == 0)
            {
                var CollectionLocation = new CollectionLocation
                {
                    CollectionID = collection.ID,
                    LocationID = _location.ID,
                };
                var temp = new List<CollectionLocation>() { CollectionLocation };
                collection.CollectionLocations = temp;
                context.SaveChanges();
                return true;
            }
            return false;
            
            
            
        }

    }
}
