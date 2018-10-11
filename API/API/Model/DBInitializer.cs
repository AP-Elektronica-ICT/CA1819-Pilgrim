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
                    Naam = "Test1",
                    value = 5,
                };

                var test2 = new Test()
                {
                    Naam = "Test2",
                    value = 10,
                };

                var test3 = new Test()
                {
                    Naam = "Test3",
                    value = 80,
                };
                context.Tests.Add(test1);
                context.Tests.Add(test2);
                context.Tests.Add(test3);
                context.SaveChanges();
            }
        }
    }
}

