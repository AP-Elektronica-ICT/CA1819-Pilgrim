using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace API.Model
{
    public class DBInitializer
    {

        public static void Initialize(LibraryContext context)
        {

            //Create the db if not yet exists
            context.Database.EnsureCreated();

            if (!context.Tests.Any())
            {
                var test1 = new Test()
                {
                    Naam = "AP hogeschool",
                    Lat= 51.23014,
                    Long = 4.415865,
                    Description = "School",
                    hint1 = "Test",
                    hint2 = "Test",
                    Question = "Test"
                };

                context.Tests.Add(test1);
                
                context.SaveChanges();
            }
        }
    }
}

