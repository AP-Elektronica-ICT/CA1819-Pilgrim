using System;
using System.Collections.Generic;
using System.Text;

namespace DataLinkLayer.Models
{
    public class CollectionLocation
    {
        public int CollectionID { get; set; }
        public int LocationID { get; set; }

        public Location Location { get; set; }
        public Collection Collection { get; set; }
    }
}
