using DataLinkLayer;
using DataLinkLayer.Models;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BusinessLayer
{
    public class PilgrimageService
    {
        private readonly Context context;

        public PilgrimageService(Context context)
        {
            this.context = context;
        }

        public List<Pilgrimage> GetPilgrimages()
        {
            return context.Pilgrimages.ToList();
        }


        public Pilgrimage GetPilgrimage(int id)
        {
            return context.Pilgrimages.Find(id);
        }

        public Pilgrimage AddPilgrimage(Pilgrimage newPilgrimage)
        {
            context.Pilgrimages.Add(newPilgrimage);
            context.SaveChanges();
            return (newPilgrimage);
        }

        public bool DeletePilgrimage(int id)
        {
            var profile = context.Profiles.Find(id);
            if (profile == null)
                return false;

            context.Profiles.Remove(profile);
            context.SaveChanges();
            return true;
        }


        public bool UpdatePilgrimage(Pilgrimage updatedPilgrimage)
        {
            ArrayList properties = new ArrayList();
            properties.Add(updatedPilgrimage.Locations);

            foreach (var item in properties)
            {

                if (item is string)
                {
                    System.Diagnostics.Debug.WriteLine("Item: " + item);
                    if (this.isEmpty(item.ToString()))
                        return false;
                }
                
            }

            var pilgrimage = context.Pilgrimages.Find(updatedPilgrimage.ID);
            if (pilgrimage == null)
                return false;
            else
            {
                pilgrimage.Locations = updatedPilgrimage.Locations;

                return true;
            }
        }

        public bool isEmpty(string input)
        {
            if (input == "")
                return true;
            return false;
        }
    }
}
