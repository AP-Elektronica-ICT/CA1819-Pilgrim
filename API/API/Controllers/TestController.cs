using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using API.Model;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers
{
    [Route("api/v1/test")]
    public class TestController : Controller
    {
        private readonly LibraryContext context;

        public TestController(LibraryContext context)
        {
            this.context = context;
        }

        // GET api/values
        [HttpGet]
        public List<Test> GetAllTests()
        {

            return context.Tests.ToList();

        }



        // POST api/values
        [HttpPost]
        public IActionResult CreateTest([FromBody] Test newTest)
        {
            var newTest1 = new Test();
            newTest1.Naam = newTest.Naam;
            newTest1.value = newTest.value;
            context.Tests.Add(newTest1);
            context.SaveChanges();
            return Created("", newTest1);
        }

    }
}
