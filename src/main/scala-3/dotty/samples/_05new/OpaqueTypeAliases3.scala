package dotty.samples._05new

object OpaqueTypeAliases3 {

  // Bounds For Opaque Type Aliases

  // Opaque type aliases can also come with bounds. Example:

  object Access {

    opaque type Permissions = Int
    opaque type PermissionChoice = Int
    opaque type Permission <: Permissions & PermissionChoice = Int
  
    extension (x: Permissions)
      def & (y: Permissions): Permissions = x & y
      def is (y: Permissions) = (x & y) == y
      def isOneOf (y: PermissionChoice) = (x & y) != 0
    extension (x: PermissionChoice)
      def | (y: PermissionChoice): PermissionChoice = x | y
  
    val NoPermission: Permission = 0
    val ReadOnly: Permission = 1
    val WriteOnly: Permission = 2
    val ReadWrite: Permissions = ReadOnly & WriteOnly
    val ReadOrWrite: PermissionChoice = ReadOnly | WriteOnly
  }

  object User {

    import Access.*
  
    case class Item(rights: Permissions)
  
    val x = Item(ReadOnly)  // OK, since Permission <: Permissions
  
    assert(x.rights.is(ReadWrite) == false)
    assert(x.rights.isOneOf(ReadOrWrite) == true)
  }
}
