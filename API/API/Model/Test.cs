using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace API.Model
{
    public class Test
    {
        public int ID { get; set; }
        public string Naam { get; set; }
        public string Description { get; set; }
        public double Lat { get; set; }
        public double Long { get; set; }
        public string Question { get; set; }
        public string hint1 { get; set; }
        public string hint2 { get; set; }
    }
}
