using DataLinkLayer;
using DataLinkLayer.Models;
using Microsoft.EntityFrameworkCore;
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
            var pilgrimage = context.Pilgrimages.Include(d => d.Locations).FirstOrDefault(p =>p.ID == id);
            foreach (Location loc in pilgrimage.Locations)
            {
                loc.base64 = Convert.ToBase64String(loc.Image);
            }
            return pilgrimage;
            

        }

        public Leaderboard GetLeaderboard(long start, long end, int? page )
        {
            IQueryable<Pilgrimage> pilgrimages = context.Pilgrimages;
            var length = 35;
            //pilgrimages = pilgrimages.Where(d => d.StartTime >= start && d.StartTime <= end);
            pilgrimages = pilgrimages.OrderBy(d => d.Time);
            pilgrimages.Include(d => d.Locations);
            var Pages = Math.Ceiling((double)pilgrimages.Count() / length);
            if (page.HasValue)
                pilgrimages.Skip(page.Value * length);
            pilgrimages = pilgrimages.Take(length);
            var pilgrimagesList = pilgrimages.ToList();
            Leaderboard leaderboard = new Leaderboard()
            {
                pilgrimages = pilgrimagesList,
                pages = 5
            };
            return leaderboard;
        }

        public Pilgrimage AddPilgrimage(Pilgrimage newPilgrimage)
        {

            ICollection<Location> locations = new List<Location>();
            foreach (Location location in newPilgrimage.Locations)
            {
                Location temp = context.Locations.FirstOrDefault(r => r.ID == location.ID);
                locations.Add(temp);
            }

            Pilgrimage pilgrimtemp = new Pilgrimage
            {
                FireBaseID = newPilgrimage.FireBaseID,
                StartTime = newPilgrimage.StartTime,
                Time = newPilgrimage.Time,
                Locations = locations,
                username = context.Profiles.FirstOrDefault(r => r.fireBaseID == newPilgrimage.FireBaseID).NickName
            };

            context.Pilgrimages.Add(pilgrimtemp);
            context.SaveChanges();
            return (pilgrimtemp);



        }

        public bool DeletePilgrimage(int id)
        {
            var pilgrimage = context.Pilgrimages.Find(id);
            if (pilgrimage == null)
                return false;

            context.Pilgrimages.Remove(pilgrimage);
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
                    if (this.IsEmpty(item.ToString()))
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

        public bool IsEmpty(string input)
        {
            if (input == "")
                return true;
            return false;
        }
    }
}
