using DataLinkLayer.Models;
using System;
using System.Linq;

namespace DataLinkLayer
{
    public class DbInit
    {
        public static void Init(Context context)
        {
            context.Database.EnsureCreated();

            if (!context.Locations.Any())
            {
                Location testLocation = new Location()
                {
                    Naam = "TestNaam",
                    CrypticClue = "Test",
                    Hint1 = "Test",
                    Hint2 = "Test",
                    Lat = 50,
                    Long = 50,
                    Description = "Test",
                    Answer = "Test",
                };
                context.Locations.Add(testLocation);
            }

            context.SaveChanges();
        }
    }
}
